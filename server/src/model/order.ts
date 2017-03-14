import mongoose = require('mongoose');

import {IFlavor, FlavorSchema} from './flavor';
import {ITopping, ToppingSchema} from './topping';
import {IContainerType, ContainerTypeSchema} from './container-type';
import {ISauce, SauceSchema} from './sauce';
import {ILocation, LocationSchema} from './location';

export interface IOrder {
    deviceID: string;
    items: Array<IOrderItem>;
    destination: ILocation;
    lastLocation: ILocation;
    requestTime: string;
    phone: string;
    hasChange: boolean;
}

export interface IOrderItem {
    containerType: IContainerType;
    flavors: Array<IFlavor>;
    sauce: ISauce;
    toppings: Array<ITopping>;
    quantity: number;
    delivered: boolean;
}

export interface IOrderModel extends IOrder, mongoose.Document {
}

export interface IOrderItemModel extends IOrderItem, mongoose.Document {
}

export var OrderItemSchema = new mongoose.Schema({
    containerType: ContainerTypeSchema,
    flavors: [FlavorSchema],
    sauce: SauceSchema,
    toppings: [ToppingSchema],
    quantity: Number,
    delivered: Boolean
});

export var OrderSchema = new mongoose.Schema({
    deviceID: String,
    items: [OrderItemSchema],
    destination: LocationSchema,
    lastLocation: LocationSchema,
    requestTime: String,
    phone: String,
    hasChange: String
});

export var Order = mongoose.model<IOrderModel>('Order', OrderSchema);
