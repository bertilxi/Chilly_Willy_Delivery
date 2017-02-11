"use strict";
const db_1 = require("./db");
const session_1 = require("./model/session");
const flavor_1 = require("./model/flavor");
const container_type_1 = require("./model/container-type");
const addin_1 = require("./model/addin");
const sauce_1 = require("./model/sauce");
const order_1 = require("./model/order");
const review_1 = require("./model/review");
var dbHelper = new db_1.DbHelper();
class Controller {
    constructor() {
        this.getLocation = (req, res) => {
            order_1.Order
                .findOne({ '_id': req.params.orderID })
                .exec((error, data) => {
                if (error) {
                    res.send(500, error.message);
                }
                res.status(200).jsonp(data.lastLocation);
            });
        };
    }
    test(req, res) {
        let mData = [];
        flavor_1.Flavor.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
        }).then(data => {
            mData.push(data);
            container_type_1.ContainerType.find((error, data) => {
                if (error) {
                    return res.send(500, error.message);
                }
            }).then(data => {
                mData.push(data);
                sauce_1.Sauce.find((error, data) => {
                    if (error) {
                        return res.send(500, error.message);
                    }
                }).then(data => {
                    mData.push(data);
                    addin_1.Addin.find((error, data) => {
                        if (error) {
                            return res.send(500, error.message);
                        }
                    }).then(data => {
                        mData.push(data);
                        res.status(200).jsonp(mData);
                    });
                });
            });
        });
    }
    root(req, res) {
        res.send("Hello Node TS");
    }
    openSession(req, res) {
        let session = new session_1.Session({
            deviceID: req.params.deviceID,
            orders: []
        });
        session.save((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data.deviceID);
        });
    }
    getFlavors(req, res) {
        flavor_1.Flavor.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }
    getContainers(req, res) {
        container_type_1.ContainerType.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }
    getAddins(req, res) {
        addin_1.Addin.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }
    getSauces(req, res) {
        sauce_1.Sauce.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }
    addOrder(req, res) {
        let order = new order_1.Order({
            containerType: req.body.containerType,
            flavors: req.body.flavors,
            sauce: req.body.sauce,
            addins: req.body.addins,
            quantity: req.body.quantity,
            delivered: false
        });
        order.save((error, data) => {
            if (error) {
                res.send(500, error.message);
            }
            res.status(200).jsonp(data._id);
        });
    }
    modifyOrder(req, res) {
        order_1.Order.update({ _id: req.params.orderID }, req.body, (error, data) => {
            if (error) {
                res.send(500, error.message);
            }
            res.status(200).jsonp(data._id);
        });
    }
    getOrders(req, res) {
        order_1.Order.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }
    addReview(req, res) {
        let review = new review_1.Review({
            rating: req.body.rating,
            img: req.body.img,
            comment: req.body.comment
        });
        review.save((error, data) => {
            if (error) {
                res.send(500, error.message);
            }
            res.status(200).jsonp(data._id);
        });
    }
}
exports.Controller = Controller;
