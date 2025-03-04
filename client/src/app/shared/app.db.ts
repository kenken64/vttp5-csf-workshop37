import Dexie, { Table } from 'dexie';
import { City } from '../model/city';


export class AppDB extends Dexie {
    cities!: Table<City, string>;
  
    constructor() {
        super('fileupload');
        this.version(2).stores({
        cities: 'code, city_name'
        });
    }

    async addCity(item: City) {
        await db.cities.put(item);
    }
}

export const db = new AppDB();
