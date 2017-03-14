import mongoose = require('mongoose');
import {ContainerType} from './model/container-type';
import {Deal} from './model/deal';
import {Flavor} from './model/flavor';
import {Order} from './model/order';
import {Review} from './model/review';
import {Sauce} from './model/sauce';
import {Topping} from './model/topping';

export class Controller {

    //
    // core
    //

    public static root(req, res) {
        res.send('Hello Node TS');
    }

    public getLastDeal(req, res) {
        Deal.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
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
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }

    public getContainers(req, res) {
        ContainerType.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }

    public getToppings(req, res) {
        Topping.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }

    public getSauces(req, res) {
        Sauce.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }

    //
    // order
    //

    public addOrder(req, res) {
        let order = new Order({
            items: req.body.items,
            destination: req.body.destination,
            lastLocation: {
                latitude: -31.619276,
                longitude: -60.683970
            },
            requestTime: req.body.requestTime,
            deviceID: req.params.deviceID,
            phone: req.params.phone,
            hasChange: req.params.phone
        });

        order.save((error, data) => {
            if (error) {
                res.send(500, error.message);
            }
            res.status(200).jsonp(data._id);
        });

    }

    public modifyOrder(req, res) {
        Order.update({_id: req.params.orderID}, req.body,
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
                return res.send(500, error.message);
            }
            data = data.filter(x => x.deviceID == req.params.deviceID);
            res.status(200).jsonp(data);
        });
    }

    public getAllOrders(req, res) {
        Order.find((error, data) => {
            if (error) {
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data);
        });
    }

    //
    // location
    //

    public getLocation = (req, res) => {
        Order.findOne({'_id': req.params.orderID})
            .exec((error, data) => {
                if (error) {
                    res.send(500, error.message);
                }
                res.status(200).jsonp(data.lastLocation);
            });
    };

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