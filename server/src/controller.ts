import mongoose = require('mongoose');
import { DbHelper } from './db';
// core
import { Session } from './model/session';
// metadata
import { Flavor } from './model/flavor';
import { ContainerType } from './model/container-type';
import { Addin } from './model/addin';
import { Sauce } from './model/sauce';
// order
import { Order } from './model/order';
// location
import { Location } from './model/location';
// review
import { Review } from './model/review';

var dbHelper: DbHelper = new DbHelper();

export class Controller {

    public test(req, res) {
        let mData: Array<any> = [];

        ContainerType
            .find((error, data) => {
                if (error) {
                    return res.send(500, error.message)
                }
            })
            .then(data => { mData.push(data); });

        Sauce
            .find((error, data) => {
                if (error) {
                    return res.send(500, error.message)
                }
            })
            .then(data => { mData.push(data); });

        Addin
            .find((error, data) => {
                if (error) {
                    return res.send(500, error.message)
                }
            })
            .then(data => {
                mData.push(data);
                res.status(200).jsonp(mData);
            });

    }

    //
    // core
    //

    public root(req, res) {
        res.send("Hello Node TS");
    }

    public openSession(req, res) {
        let session = new Session({
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

    //
    // order
    //

    public addOrder(req, res) {
        let order = new Order({
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

    public modifyOrder(req, res) {

    }

    public getOrders(req, res) {
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



/*
exports.findAllMissingDogs = function (req, res) {
    MissingDog.find(function (err, missingDog) {
        if (err) res.send(500, err.message);

        console.log('GET /missingDog');
        res.status(200).jsonp(missingDog);
    });
};

exports.findMissingDogs = function (req, res) {
    MissingDog.findById(req.params.id, function (err, missingDog) {
        if (err) return res.send(500, err.message);

        console.log('GET /missingDog/' + req.params.id);
        res.status(200).jsonp(missingDog);
    });
};

exports.findById = function (req, res) {
    MissingDog.findById(req.params.id, function (err, missingDog) {

        if (err) {
            return res.status(500).send(err.message)
        }
        console.log('GET /missingDog/' + req.params.id);
        res.status(200).jsonp(missingDog);
    });
};



exports.addMissingDog = function (req, res) {
    console.log('POST');
    console.log(req.body);

    var missingDog = new MissingDog({
        raza: req.body.raza,
        nombre: req.body.nombre,
        descripcion: req.body.descripcion,
        lugar: req.body.lugar,
        encontrado: req.body.encontrado
    });

    missingDog.save(function (err, tvshow) {
        if (err) return res.send(500, err.message);
        res.status(200).jsonp(missingDog);
    });
};


exports.updateMissingDog = function (req, res) {
    MissingDog.findById(req.params.id, function (err, missingDog) {

        console.log('POST');
        console.log(req.body);

        missingDog.raza = req.body.raza;
        missingDog.nombre = req.body.nombre;
        missingDog.descripcion = req.body.descripcion;
        missingDog.lugar = req.body.lugar;
        missingDog.encontrado = req.body.encontrado;

        missingDog.save(function (err) {
            if (err) return res.send(500, err.message);
            res.status(200).jsonp(missingDog);
        });
    });
};


exports.deleteMissingDog = function (req, res) {
    MissingDog.findById(req.params.id, function (err, missingDog) {
        missingDog.remove(function (err) {
            if (err) return res.send(500, err.message);
            res.status(200);
        })
    });
};
*/