import mongoose = require('mongoose');
// models
import Location = require('./model/location');
import Notification = require('./model/notification');
import Order = require('./model/order');
import Review = require('./model/review');
import Session = require('./model/session');

export class Controller {

    constructor() { }

    public test = (req, res) => {
        res.send("Hello Node TS");
    }

    public reset = (req, res) => {

        Location.remove({}, error => {
            if (error) { return res.send(500, error.message) }
            res.status(200).jsonp();
        });
        Session.remove({}, error => {
            if (error) { return res.send(500, error.message) }
            res.status(200).jsonp();
        });
        Notification.remove({}, error => {
            if (error) { return res.send(500, error.message) }
            res.status(200).jsonp();
        });
        Order.remove({}, error => {
            if (error) { return res.send(500, error.message) }
            res.status(200).jsonp();
        });
        Review.remove({}, error => {
            if (error) { return res.send(500, error.message) }
            res.status(200).jsonp();
        });
    }

    public getSession = (req, res) => {

        let session = new Session();

        session.save((error, mSession) => {
            if (error) return res.send(500, error.message);
            res.status(200).jsonp(mSession);
        });
    }
    public getSessions = (req, res) => {
        Session.find((error, session) => {
            if (error) { return res.send(500, error.message) }
            console.log('GET /sessions');
            res.status(200).jsonp(session);
        });
    }

    public getLocation = (req, res) => {
        Location.findById(req.params.id, (error, location) => {
            if (error) { return res.send(500, error.message) }
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
            if (error) { return res.send(500, error.message) }
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
