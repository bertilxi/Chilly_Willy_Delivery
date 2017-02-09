import * as _ from 'lodash';

import mongoose = require('mongoose');
// import Location = require('./model/location');
// import Notification = require('./model/notification');
// import Order = require('./model/order');
// import Review = require('./model/review');
// import Session = require('./model/session');
import Flavor = require('./model/flavor');

// var LocationData = require('./data/location');
// var NotificationData = require('./data/location');
// var OrderData = require('./data/location');
// var ReviewData = require('./data/location');
// var SessionData = require('./data/location');

import { flavors } from './data/flavors';
import { addins } from './data/addins';

export class DbHelper {
    constructor() {
        this.resetMetadata();
    }

    public resetMetadata() {
        Flavor.remove({}, error => {
            if (error) {
                console.log(error);
            }
        });
    }

    public init(): void {
        console.log(flavors);

        for (let x of flavors) {
            new Flavor(x).save();
        }
    }
    /*
        public reset(): void {
    
    
            Location.find().remove();
            Notification.find().remove();
            Order.find().remove();
            Review.find().remove();
            Session.find().remove();
    
            for (let x of LocationData) {
                new Location(x).save();
            }
            for (let x of NotificationData) {
                new Notification(x).save();
            }
            for (let x of OrderData) {
                new Order(x).save();
            }
            for (let x of ReviewData) {
                new Review(x).save();
            }
            for (let x of SessionData) {
                new Session(x).save();
            }
    
    
    
        }
    */
}
