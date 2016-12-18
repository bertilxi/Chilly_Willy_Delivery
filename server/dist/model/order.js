"use strict";
const mongoose = require("mongoose");
var OrderItemSchema = new mongoose.Schema({
    containerType: String,
    containerSize: String,
    flavors: [String],
    sauce: String,
    addins: [String],
    quantity: Number
});
var OrderSchema = new mongoose.Schema({
    items: [OrderItemSchema]
});
var Order = mongoose.model("User", OrderSchema);
module.exports = Order;
