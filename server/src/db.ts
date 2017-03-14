import {containerTypes} from './data/container-types';
import {deals} from './data/deals';
// data
import {flavors} from './data/flavors';
import {sauces} from './data/sauce';
import {toppings} from './data/toppings';
import {ContainerType} from './model/container-type';
import {Deal} from './model/deal';
// models
import {Flavor} from './model/flavor';
import {Order} from './model/order';
import {Sauce} from './model/sauce';
import {Topping} from './model/topping';
import mongoose = require('mongoose');

export class DbHelper {
    constructor() {
        this.resetMetadata();
    }

    public resetMetadata() {

        Flavor.remove({}, () => {
        });
        ContainerType.remove({}, () => {
        });
        Sauce.remove({}, () => {
        });
        Topping.remove({}, () => {
        });
        Order.remove({}, () => {
        });
        Deal.remove({}, () => {
        });

    }

    public init(): void {

        flavors.forEach(x => new Flavor(x).save());
        containerTypes.forEach(x => new ContainerType(x).save());
        sauces.forEach(x => new Sauce(x).save());
        toppings.forEach(x => new Topping(x).save());
        deals.forEach(x => new Deal(x).save());
    }
}
