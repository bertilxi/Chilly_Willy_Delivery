"use strict";
const mongoose = require("mongoose");
const flavor_1 = require("./flavor");
const addin_1 = require("./addin");
const container_type_1 = require("./container-type");
const sauce_1 = require("./sauce");
const location_1 = require("./location");
exports.OrderItemSchema = new mongoose.Schema({
    containerType: container_type_1.ContainerTypeSchema,
    flavors: [flavor_1.FlavorSchema],
    sauce: sauce_1.SauceSchema,
    addins: [addin_1.AddinSchema],
    quantity: Number,
    delivered: Boolean
});
exports.OrderSchema = new mongoose.Schema({
    deviceID: String,
    items: [exports.OrderItemSchema],
    destination: location_1.LocationSchema,
    lastLocation: location_1.LocationSchema,
    requestTime: String
});
exports.Order = mongoose.model("Order", exports.OrderSchema);
