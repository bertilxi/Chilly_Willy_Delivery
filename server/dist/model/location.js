"use strict";
const mongoose = require("mongoose");
var LocationSchema = new mongoose.Schema({
    latitude: Number,
    longitud: Number,
    timeStamp: Number
});
var Location = mongoose.model("Location", LocationSchema);
module.exports = Location;
