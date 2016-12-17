import * as bodyParser from "body-parser";
import * as cookieParser from "cookie-parser";
import * as express from "express";
import * as logger from "morgan";
import * as path from "path";
import { Router } from 'express';
import errorHandler = require("errorhandler");
import methodOverride = require("method-override");
import { Controller } from './controller'

export class Server {

    public app: express.Application;
    public router = express.Router();

    public static bootstrap(): Server {
        return new Server();
    }

    constructor() {
        //create expressjs application
        this.app = express();

        //configure application
        this.config();

        //add api
        this.api();
    }

    public api() {

        this.router.route('/').get().post();
/*
        var missingDog = express.Router();

        missingDog.route('/missingDog')
            .get(MissingDogCtrl.findAllMissingDogs)
            .post(MissingDogCtrl.addMissingDog);

        missingDog.route('/missingDog/:id')
            .get(MissingDogCtrl.findById)
            .put(MissingDogCtrl.updateMissingDog)
            .delete(MissingDogCtrl.deleteMissingDog);

        app.use('/api', missingDog);
        */
    }

    public config() {
        //add static paths
        this.app.use(express.static(path.join(__dirname, "public")));

        //configure pug
        this.app.set("views", path.join(__dirname, "views"));
        this.app.set("view engine", "pug");

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
        this.app.use(function (err: any, req: express.Request, res: express.Response, next: express.NextFunction) {
            err.status = 404;
            next(err);
        });

        //error handling
        this.app.use(errorHandler());

        // api
        this.app.use(this.router);
    }

}