"use strict";
const db_1 = require("./db");
const flavor_1 = require("./model/flavor");
const container_type_1 = require("./model/container-type");
const topping_1 = require("./model/topping");
const sauce_1 = require("./model/sauce");
const deal_1 = require("./model/deal");
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
    }
    root(req, res) {
        res.send('Hello Node TS');
    }
    getLastDeal(req, res) {
        deal_1.Deal.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
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
    getToppings(req, res) {
        topping_1.Topping.find((error, data) => {
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
    getMetadata(req, res) {
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
                    topping_1.Topping.find((error, data) => {
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
    addOrder(req, res) {
        let order = new order_1.Order({
            items: req.body.items,
            destination: req.body.destination,
            lastLocation: req.body.lastLocation,
            requestTime: req.body.requestTime,
            deviceID: req.params.deviceID,
            phone: req.params.phone,
            hasChange: req.params.phone,
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
            data = data.filter(x => x.deviceID == req.params.deviceID);
            res.status(200).jsonp(data);
        });
    }
    getAllOrders(req, res) {
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
