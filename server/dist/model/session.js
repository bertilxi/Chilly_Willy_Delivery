"use strict";
const mongoose = require("mongoose");
const order_1 = require("./order");
exports.SessionSchema = new mongoose.Schema({
    deviceID: Number,
    orders: [order_1.OrderSchema]
});
exports.Session = mongoose.model("Session", exports.SessionSchema);
