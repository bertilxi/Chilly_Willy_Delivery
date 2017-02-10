"use strict";
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const express = require("express");
const logger = require("morgan");
const controller_1 = require("./controller");
const errorHandler = require("errorhandler");
const methodOverride = require("method-override");
const mongoose = require("mongoose");
const db_1 = require("./db");
var mCtrl = new controller_1.Controller();
var dbHelper = new db_1.DbHelper();
class Server {
    constructor(ctrl) {
        this.ctrl = ctrl;
        this.router = express.Router();
        this.app = express();
        this.config();
        this.api();
        dbHelper.init();
    }
    static bootstrap() {
        return new Server(mCtrl);
    }
    api() {
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
    config() {
        this.app.use('/static', express.static(__dirname + '/public'));
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
