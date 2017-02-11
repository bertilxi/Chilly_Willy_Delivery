import mongoose = require("mongoose");

import { IFlavor, FlavorSchema } from './flavor';
import { IAddin, AddinSchema } from './addin';
import { IContainerType, ContainerTypeSchema } from './container-type';
import { ISauce, SauceSchema } from './sauce';
import { ILocation, LocationSchema } from './location';

export interface IOrder {
    items: Array<IOrderItem>;
    lastLocation: ILocation;
}

export interface IOrderItem {
    containerType: IContainerType;
    flavors: Array<IFlavor>;
    sauce: ISauce;
    addins: Array<IAddin>;
    quantity: number;
    delivered: boolean;
}

export interface IOrderModel extends IOrder, mongoose.Document { }

export interface IOrderItemModel extends IOrderItem, mongoose.Document { }

export var OrderItemSchema = new mongoose.Schema({
    containerType: ContainerTypeSchema,
    flavors: [FlavorSchema],
    sauce: SauceSchema,
    addins: [AddinSchema],
    quantity: Number,
    delivered: Boolean
});

export var OrderSchema = new mongoose.Schema({
    items: [OrderItemSchema],
    lastLocation: LocationSchema
});

export var Order = mongoose.model<IOrderModel>("Order", OrderSchema);
