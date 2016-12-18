import mongoose = require("mongoose");

interface ILocation {
    latitude: number;
    longitud: number;
    timeStamp: number;
}

var LocationSchema = new mongoose.Schema({
    latitude: Number,
    longitud: Number,
    timeStamp: Number
});

interface ILocationModel extends ILocation, mongoose.Document { }

var Location = mongoose.model<ILocationModel>("Location", LocationSchema);

export = Location;