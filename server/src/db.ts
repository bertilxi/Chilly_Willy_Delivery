import * as _ from 'lodash';

import mongoose = require('mongoose');

import Flavor = require('./model/flavor');
import ContainerSize = require('./model/container-size');
import ContainerType = require('./model/container-type');
import Sauce = require('./model/sauce');
import Addin = require('./model/addin');



import { flavors } from './data/flavors';
import { containerSizes } from './data/container-size';
import { containerTypes } from './data/container-types';
import { sauces } from './data/sauce';
import { addins } from './data/addins';

export class DbHelper {
    constructor() {
        this.resetMetadata();
    }

    public resetMetadata() {

        Flavor.remove({}, () => { });
        ContainerSize.remove({}, () => { });
        ContainerType.remove({}, () => { });
        Sauce.remove({}, () => { });
        Addin.remove({}, () => { });

    }

    public init(): void {

        flavors.forEach(x => { new Flavor(x).save(); });
        containerSizes.forEach(x => { new ContainerSize(x).save(); });
        containerTypes.forEach(x => { new ContainerType(x).save(); });
        sauces.forEach(x => { new Sauce(x).save() });
        addins.forEach(x => { new Addin(x).save(); });

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
