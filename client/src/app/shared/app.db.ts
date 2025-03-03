import Dexie, { Table } from 'dexie';
import { City } from '../model/city';


export class AppDB extends Dexie {
    cities!: Table<City, number>;
  
    constructor() {
        super('fileupload');
        this.version(1).stores({
        cities: '++id, code'
        });
    }

    async addCity(item: City) {
        const cityListId = await db.cities.add(item);
        console.log(cityListId)
    }
}

export const db = new AppDB();
