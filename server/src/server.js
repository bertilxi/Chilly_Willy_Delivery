"use strict";
var bodyParser = require("body-parser");
var cookieParser = require("cookie-parser");
var express = require("express");
var logger = require("morgan");
var path = require("path");
var controller_1 = require("./controller");
var errorHandler = require("errorhandler");
var methodOverride = require("method-override");
var mongoose = require("mongoose");
var mCtrl = new controller_1.Controller();
var Server = (function () {
    function Server(ctrl) {
        this.ctrl = ctrl;
        this.router = express.Router();
        this.app = express();
        this.config();
        this.api();
    }
    Server.bootstrap = function () {
        return new Server(mCtrl);
    };
    Server.prototype.api = function () {
        this.router.route('/')
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
    };
    Server.prototype.config = function () {
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
        this.app.use(function (err, req, res, next) {
            err.status = 404;
            next(err);
        });
        //error handling
        this.app.use(errorHandler());
        // api
        this.app.use(this.router);
        mongoose.connect('mongodb://localhost/api', function (error, res) {
            if (error) {
                throw error;
            }
            ;
            console.log('Connected to Database');
        });
    };
    return Server;
}());
exports.Server = Server;
