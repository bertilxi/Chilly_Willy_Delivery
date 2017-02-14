import * as _ from 'lodash';
import mongoose = require('mongoose');

// models
import { Flavor } from './model/flavor';
import { ContainerType } from './model/container-type';
import { Topping } from './model/topping';
import { Sauce } from './model/sauce';

import { Order } from './model/order';
// data
import { flavors } from './data/flavors';
import { containerTypes } from './data/container-types';
import { sauces } from './data/sauce';
import { toppings } from './data/toppings';

export class DbHelper {
    constructor() {
        this.resetMetadata();
    }

    public resetMetadata() {

        Flavor.remove({}, () => { });
        ContainerType.remove({}, () => { });
        Sauce.remove({}, () => { });
        Topping.remove({}, () => { });
        Order.remove({}, () => { });

    }

    public init(): void {

        flavors.forEach(x => { new Flavor(x).save(); });
        containerTypes.forEach(x => { new ContainerType(x).save(); });
        sauces.forEach(x => { new Sauce(x).save() });
        toppings.forEach(x => { new Topping(x).save(); });

    }
}
