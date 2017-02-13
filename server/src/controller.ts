import mongoose = require('mongoose');
import { DbHelper } from './db';

// metadata
import { Flavor } from './model/flavor';
import { ContainerType } from './model/container-type';
import { Addin } from './model/addin';
import { Sauce } from './model/sauce';
// core
import { Notification } from './model/notification';
// order
import { Order } from './model/order';
// location
import { Location } from './model/location';
// review
import { Review } from './model/review';

var dbHelper: DbHelper = new DbHelper();

export class Controller {

    public test(req, res) {

    }

    //
    // core
    //

    public root(req, res) {
        res.send('Hello Node TS');
    }

    public getLastDeal(req, res) {
        Notification.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    //
    // metadata
    //

    public getFlavors(req, res) {
        Flavor.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    public getContainers(req, res) {
        ContainerType.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    public getAddins(req, res) {
        Addin.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    public getSauces(req, res) {
        Sauce.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    // TODO: improve this
    public getMetadata(req, res) {
        let mData: Array<any> = [];

        Flavor.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
        }).then(data => {

            mData.push(data);
            ContainerType.find((error, data) => {
                if (error) {
                    return res.send(500, error.message)
                }
            }).then(data => {
                mData.push(data);
                Sauce.find((error, data) => {
                    if (error) {
                        return res.send(500, error.message)
                    }
                }).then(data => {
                    mData.push(data);
                    Addin.find((error, data) => {
                        if (error) {
                            return res.send(500, error.message)
                        }
                    }).then(data => {
                        mData.push(data);
                        res.status(200).jsonp(mData);
                    });
                });

            });

        });

    }

    //
    // order
    //

    public addOrder(req, res) {
        let order = new Order({
            items: req.body.items,
            destination: req.body.destination,
            lastLocation: req.body.lastLocation,
            requestTime: req.body.requestTime,
            deviceID: req.params.deviceID
        });

        order.save((error, data) => {
            if (error) {
                res.send(500, error.message);
            }
            res.status(200).jsonp(data._id);
        });

    }

    public modifyOrder(req, res) {
        Order.update({ _id: req.params.orderID }, req.body,
            (error, data) => {
                if (error) {
                    res.send(500, error.message);
                }
                res.status(200).jsonp(data._id);
            });
    }

    public getOrders(req, res) {

        Order.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            data = data.filter(x => x.deviceID == req.params.deviceID);
            res.status(200).jsonp(data);
        });
    }

    public getAllOrders(req, res) {
        Order.find((error, data) => {
            if (error) {
                return res.send(500, error.message)
            }
            res.status(200).jsonp(data);
        });
    }

    //
    // location
    //

    public getLocation = (req, res) => {
        Order
            .findOne({ '_id': req.params.orderID })
            .exec((error, data) => {
                if (error) {
                    res.send(500, error.message)
                }
                res.status(200).jsonp(data.lastLocation);
            })
    }

    //
    // review
    //

    public addReview(req, res) {
        let review = new Review({
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