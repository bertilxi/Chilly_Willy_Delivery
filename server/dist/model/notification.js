"use strict";
const mongoose = require("mongoose");
var DealSchema = new mongoose.Schema({
    title: String,
    description: String
});
var NotificationSchema = new mongoose.Schema({
    deals: [DealSchema]
});
var Notification = mongoose.model("Notification", NotificationSchema);
module.exports = Notification;
