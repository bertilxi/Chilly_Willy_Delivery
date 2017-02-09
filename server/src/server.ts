import * as bodyParser from "body-parser";
import * as cookieParser from "cookie-parser";
import * as express from "express";
import * as logger from "morgan";
import * as path from "path";
import { Router } from 'express';
import { Controller } from './controller';
import errorHandler = require("errorhandler");
import methodOverride = require("method-override");
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

    public api() {

        this.router.route('/')
            .get(this.ctrl.root);

        this.router.route('/test')
            .get(this.ctrl.test);

        this.router.route('/reset')
            .post(this.ctrl.reset);

        this.router.route('/session')
            .get(this.ctrl.getSession);

        this.router.route('/sessions')
            .get(this.ctrl.getSessions);

        this.router.route('/session/:sessionId/order/:orderId')
            .get(this.ctrl.getOrder);

        this.router.route('/session/:sessionId/order')
            .get(this.ctrl.getOrders)
            .post(this.ctrl.addOrder);

        this.router.route('/session/:sessionId/order/:orderId/location')
            .get(this.ctrl.getLocation)
            .post(this.ctrl.addLocation)
            .put(this.ctrl.updateLocation);

        this.router.route('/notification')
            .get(this.ctrl.getNotification);

        this.router.route('/review')
            .post(this.ctrl.addReview);

    }

    public config() {
        //add static paths
        this.app.use(express.static(path.join(__dirname, "public")));

        //mount logger
        this.app.use(logger("dev"));

        //mount json form parser
        this.app.use(bodyParser.json());

        //mount query string parser
        this.app.use(bodyParser.urlencoded({
            extended: true
        }));

        //mount cookie parker
        this.app.use(cookieParser("SECRET_GOES_HERE"));

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