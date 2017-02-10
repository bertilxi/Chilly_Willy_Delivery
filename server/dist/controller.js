"use strict";
const db_1 = require("./db");
const ContainerSize = require("./model/container-size");
const ContainerType = require("./model/container-type");
const Sauce = require("./model/sauce");
const Addin = require("./model/addin");
const Location = require("./model/location");
const Notification = require("./model/notification");
const Order = require("./model/order");
const Review = require("./model/review");
const Session = require("./model/session");
var dbHelper = new db_1.DbHelper();
class Controller {
    constructor() {
        this.reset = (req, res) => {
            Location.remove({}, error => {
                if (error) {
                    return res.send(500, error.message);
                }
                res.status(200).jsonp();
            });
            Session.remove({}, error => {
                if (error) {
                    return res.send(500, error.message);
                }
                res.status(200).jsonp();
            });
            Notification.remove({}, error => {
                if (error) {
                    return res.send(500, error.message);
                }
                res.status(200).jsonp();
            });
            Order.remove({}, error => {
                if (error) {
                    return res.send(500, error.message);
                }
                res.status(200).jsonp();
            });
            Review.remove({}, error => {
                if (error) {
                    return res.send(500, error.message);
                }
                res.status(200).jsonp();
            });
        };
        this.getSession = (req, res) => {
            let session = new Session();
            session.save((error, mSession) => {
                if (error)
                    return res.send(500, error.message);
                res.status(200).jsonp(mSession);
            });
        };
        this.getSessions = (req, res) => {
            Session.find((error, session) => {
                if (error) {
                    return res.send(500, error.message);
                }
                console.log('GET /sessions');
                res.status(200).jsonp(session);
            });
        };
        this.getLocation = (req, res) => {
            Location.findById(req.params.id, (error, location) => {
                if (error) {
                    return res.send(500, error.message);
                }
                console.log('GET /location/' + req.params.id);
                res.status(200).jsonp(location);
            });
        };
        this.addLocation = (req, res) => {
            console.log('POST');
            console.log(req.body);
            let location = new Location({
                latitude: req.body.latitude,
                longitud: req.body.longitud,
                timeStamp: req.body.timeStamp
            });
            location.save((err, mLocation) => {
                if (err)
                    return res.send(500, err.message);
                res.status(200).jsonp(mLocation);
            });
        };
        this.getLocations = (req, res) => {
            Location.find((error, location) => {
                if (error) {
                    return res.send(500, error.message);
                }
                console.log('GET /location/');
                res.status(200).jsonp(location);
            });
        };
        this.getOrder = (req, res) => { };
        this.getOrders = (req, res) => { };
        this.addOrder = (req, res) => { };
        this.updateLocation = (req, res) => { };
        this.getNotification = (req, res) => { };
        this.addReview = (req, res) => { };
    }
    test(req, res) {
        let mData = [];
        ContainerSize
            .find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
        })
            .then(data => { mData.push(data); });
        ContainerType
            .find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
        })
            .then(data => { mData.push(data); });
        Sauce
            .find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
        })
            .then(data => { mData.push(data); });
        Addin
            .find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
        })
            .then(data => {
            mData.push(data);
            res.status(200).jsonp(mData);
        });
    }
    root(req, res) {
        res.send("Hello Node TS");
    }
}
exports.Controller = Controller;
