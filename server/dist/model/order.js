"use strict";
const mongoose = require("mongoose");
var OrderItemSchema = new mongoose.Schema({
    containerType: String,
    containerSize: String,
    flavors: [String],
    sauce: String,
    addins: [String],
    quantity: Number,
    delivered: Boolean
});
var OrderSchema = new mongoose.Schema({
    items: [OrderItemSchema]
});
var Order = mongoose.model("Order", OrderSchema);
module.exports = Order;
