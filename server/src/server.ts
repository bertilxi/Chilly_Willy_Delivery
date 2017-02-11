import * as bodyParser from 'body-parser';
import * as cookieParser from 'cookie-parser';
import * as express from 'express';
import * as logger from 'morgan';
import * as path from 'path';
import { Router } from 'express';
import { Controller } from './controller';
import errorHandler = require('errorhandler');
import methodOverride = require('method-override');
import mongoose = require('mongoose');
import { DbHelper } from './db';

var mCtrl = new Controller();
var dbHelper = new DbHelper();

export class Server {

    public app: express.Application;
    public router = express.Router();

    public static bootstrap(): Server {
        return new Server(mCtrl);
    }

    constructor(private ctrl: Controller) {
        this.app = express();
        this.config();
        this.api();
        dbHelper.init();
    }

    private api(): void {

        this.router.route('/').get(this.ctrl.root);
        this.router.route('/test').get(this.ctrl.test);

        //
        // metadata
        //
        this.router.route('/metadata').get(this.ctrl.getMetadata)
        this.router.route('/flavors').get(this.ctrl.getFlavors);
        this.router.route('/containers').get(this.ctrl.getContainers);
        this.router.route('/addins').get(this.ctrl.getAddins);
        this.router.route('/sauces').get(this.ctrl.getSauces);

        //
        // core
        //

        this.router.route('/session/:deviceID').post(this.ctrl.openSession);

        //
        // Order
        //

        this.router.route('/order').post(this.ctrl.addOrder);
        this.router.route('/order/:orderID').put(this.ctrl.modifyOrder);
        this.router.route('/orders/:deviceID').get(this.ctrl.getOrders);

        //
        // Location
        //

        this.router.route('/location/:orderID').get(this.ctrl.getLocation);

        //
        // Review
        //

        this.router.route('/review').post();

    }

    private config(): void {
        //add static paths
        this.app.use('/static', express.static(__dirname + '/public'));

        //mount logger
        this.app.use(logger('dev'));

        //mount json form parser
        this.app.use(bodyParser.json());

        //mount query string parser
        this.app.use(bodyParser.urlencoded({
            extended: true
        }));

        //mount cookie parker
        this.app.use(cookieParser('SECRET_GOES_HERE'));

        //mount override?
        this.app.use(methodOverride());

        // catch 404 and forward to error handler
        this.app.use((err: any, req: express.Request, res: express.Response, next: express.NextFunction) => {
            err.status = 404;
            next(err);
        });

        //error handling
        this.app.use(errorHandler());

        // api
        this.app.use(this.router);
        mongoose.connect('mongodb://localhost/api', (error, res) => {
            if (error) { throw error };
            console.log('Connected to Database');
        });
    }

}