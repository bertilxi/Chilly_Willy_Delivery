
import mongoose = require('mongoose');
import model = mongoose.model('model');

export class Controller {

    constructor() {}

public 


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