import mongoose = require("mongoose");

import { IOrder, OrderSchema } from './order';

export interface ISession {
    deviceID: number;
    orders: Array<IOrder>;
}

export var SessionSchema = new mongoose.Schema({
    deviceID: Number,
    orders: [OrderSchema]
});

export interface ISessionModel extends ISession, mongoose.Document { }

export var Session = mongoose.model<ISessionModel>("Session", SessionSchema);