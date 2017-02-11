
import mongoose = require("mongoose");

export interface INotification {
    deals: Array<IDeal>;
}

export interface IDeal {
    title: string;
    description: string;
}

export var DealSchema = new mongoose.Schema({
    title: String,
    description: String
});

export var NotificationSchema = new mongoose.Schema({
    deals: [DealSchema]
});

export interface INotificationModel extends INotification, mongoose.Document { }

export var Notification = mongoose.model<INotificationModel>("Notification", NotificationSchema);
