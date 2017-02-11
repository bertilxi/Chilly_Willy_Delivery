"use strict";
const mongoose = require("mongoose");
exports.LocationSchema = new mongoose.Schema({
    latitude: Number,
    longitud: Number
});
exports.Location = mongoose.model("Location", exports.LocationSchema);
