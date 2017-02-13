import * as _ from 'lodash';
import mongoose = require('mongoose');

// models
import { Flavor } from './model/flavor';
import { ContainerType } from './model/container-type';
import { Addin } from './model/addin';
import { Sauce } from './model/sauce';
import { Deal } from './model/deal';
import { Order } from './model/order';
// data
import { flavors } from './data/flavors';
import { containerTypes } from './data/container-types';
import { sauces } from './data/sauce';
import { addins } from './data/addins';
import { deals } from './data/deals';

export class DbHelper {
    constructor() {
        this.resetMetadata();
    }

    public resetMetadata() {

        Flavor.remove({}, () => { });
        ContainerType.remove({}, () => { });
        Sauce.remove({}, () => { });
        Addin.remove({}, () => { });
        Order.remove({}, () => { });
        Deal.remove({}, () => { });

    }

    public init(): void {

        flavors.forEach(x => new Flavor(x).save());
        containerTypes.forEach(x => new ContainerType(x).save());
        sauces.forEach(x => new Sauce(x).save());
        addins.forEach(x => new Addin(x).save());
        deals.forEach(x => new Deal(x).save());

    }
}
