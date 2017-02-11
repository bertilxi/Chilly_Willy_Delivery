import mongoose = require('mongoose');
import { DbHelper } from './db';

import Flavor = require('./model/flavor');
import ContainerType = require('./model/container-type');
import Sauce = require('./model/sauce');
import Addin = require('./model/addin');

import Location = require('./model/location');
import Notification = require('./model/notification');
import Order = require('./model/order');
import Review = require('./model/review');
import Session = require('./model/session');



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
        session.save((error, data)=>{
            if(error){
                return res.send(500, error.message);
            }
            res.status(200).jsonp(data.deviceID);
        });
    }

    //
    // order
    //

    public addOrder(req, res){
        let order = new Order({
            container
        });
    }



    public getLocation = (req, res) => {
        Location.findById(req.params.id, (error, location) => {
            if (error) {
                return res.send(500, error.message)
            }
            console.log('GET /location/' + req.params.id);
            res.status(200).jsonp(location);
        });
    }
    public addLocation = (req, res) => {
        console.log('POST');
        console.log(req.body);

        let location = new Location({
            latitude: req.body.latitude,
            longitud: req.body.longitud,
            timeStamp: req.body.timeStamp
        });

        location.save((err, mLocation) => {
            if (err) return res.send(500, err.message);
            res.status(200).jsonp(mLocation);
        });
    }

    public getLocations = (req, res) => {
        Location.find((error, location) => {
            if (error) {
                return res.send(500, error.message)
            }
            console.log('GET /location/');
            res.status(200).jsonp(location);
        });
    }

    public getOrder = (req, res) => { }

    public getOrders = (req, res) => { }

    public addOrder = (req, res) => { }

    public updateLocation = (req, res) => { }

    public getNotification = (req, res) => { }

    public addReview = (req, res) => { }

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