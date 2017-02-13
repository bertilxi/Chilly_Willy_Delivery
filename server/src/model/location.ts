import mongoose = require("mongoose");

export interface ILocation {
    latitude: number;
    longitud: number;
}

export var LocationSchema = new mongoose.Schema({
    latitude: Number,
    longitude: Number
});

export interface ILocationModel extends ILocation, mongoose.Document { }

export var Location = mongoose.model<ILocationModel>("Location", LocationSchema);
