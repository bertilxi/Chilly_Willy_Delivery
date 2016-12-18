"use strict";
const Location = require("./model/location");
const Session = require("./model/session");
class Controller {
    constructor() {
        this.test = (req, res) => {
            res.send("Hello Node TS");
        };
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
        this.setLocation = (req, res) => {
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
    }
}
exports.Controller = Controller;
