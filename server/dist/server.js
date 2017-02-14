"use strict";
const bodyParser = require("body-parser");
const cookieParser = require("cookie-parser");
const express = require("express");
const logger = require("morgan");
const errorHandler = require("errorhandler");
const methodOverride = require("method-override");
const mongoose = require("mongoose");
const controller_1 = require("./controller");
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
        this.router.route('/').get(this.ctrl.root);
        this.router.route('/test').get(this.ctrl.test);
        this.router.route('/metadata').get(this.ctrl.getMetadata);
        this.router.route('/flavors').get(this.ctrl.getFlavors);
        this.router.route('/containers').get(this.ctrl.getContainers);
        this.router.route('/toppings').get(this.ctrl.getToppings);
        this.router.route('/sauces').get(this.ctrl.getSauces);
        this.router.route('/session/:deviceID/order').post(this.ctrl.addOrder);
        this.router.route('/session/:deviceID/order/:orderID').put(this.ctrl.modifyOrder);
        this.router.route('/session/:deviceID/orders').get(this.ctrl.getOrders);
        this.router.route('/orders').get(this.ctrl.getAllOrders);
        this.router.route('/location/:orderID').get(this.ctrl.getLocation);
        this.router.route('/review').post();
    }
    config() {
        this.app.use('/static', express.static(__dirname + '/public'));
        this.app.use(logger('dev'));
        this.app.use(bodyParser.json());
        this.app.use(bodyParser.urlencoded({
            extended: true
        }));
        this.app.use(cookieParser('SECRET_GOES_HERE'));
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
