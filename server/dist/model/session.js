"use strict";
const mongoose = require("mongoose");
var SessionSchema = new mongoose.Schema({
    id: Number
});
var Session = mongoose.model("Session", SessionSchema);
module.exports = Session;
