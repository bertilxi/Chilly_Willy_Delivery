"use strict";
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const express = require("express");
const logger = require("morgan");
const path = require("path");
const controller_1 = require("./controller");
const errorHandler = require("errorhandler");
const methodOverride = require("method-override");
const mongoose = require("mongoose");
var mCtrl = new controller_1.Controller();
class Server {
    constructor(ctrl) {
        this.ctrl = ctrl;
        this.router = express.Router();
        this.app = express();
        this.config();
        this.api();
    }
    static bootstrap() {
        return new Server(mCtrl);
    }
    api() {
        this.router.route('/reset').post(this.ctrl.reset);
        this.router.route('/session')
            .get(this.ctrl.getSession);
        this.router.route('/sessions')
            .get(this.ctrl.getSessions);
        this.router.route('/')
            .get(this.ctrl.test);
        this.router.route('/location')
            .get(this.ctrl.getLocations)
            .post(this.ctrl.setLocation);
        this.router.route('/location/:id')
            .get(this.ctrl.getLocation);
    }
    config() {
        this.app.use(express.static(path.join(__dirname, "public")));
        this.app.use(logger("dev"));
        this.app.use(bodyParser.json());
        this.app.use(bodyParser.urlencoded({
            extended: true
        }));
        this.app.use(cookieParser("SECRET_GOES_HERE"));
        this.app.use(methodOverride());
        this.app.use((err, req, res, next) => {
            err.status = 404;
            next(err);
        });
        this.app.use(errorHandler());
        this.app.use(this.router);
        mongoose.connect('mongodb://localhost/api', (error, res) => {
            if (error) {
                throw error;
            }
            ;
            console.log('Connected to Database');
        });
    }
}
exports.Server = Server;
