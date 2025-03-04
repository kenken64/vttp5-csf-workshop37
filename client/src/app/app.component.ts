import { Component, OnInit } from '@angular/core';
import { CitiesService } from './services/cities.service';
import { City } from './model/city';
import { db } from './shared/app.db';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrl: './app.component.css',
    standalone: false
})
export class AppComponent implements OnInit{
  title = 'client';
  constructor(private citiesSvc : CitiesService) {}
  cities: City[] = [];
  
  // Mark ngOnInit as async
  async ngOnInit(): Promise<void> {
    try {
      await db.cities.clear();
      // Wait for the observable to complete
      this.cities = await this.citiesSvc.getCities();
      this.cities.forEach(async(data)=>{
        console.log(data.code);
        console.log(data.city_name);
        await db.addCity({
          code: data.code,
          city_name: data.city_name
        });
      })
      
      console.log('Cities fetched:', this.cities);
    } catch (err) {
      console.error('Error fetching cities:', err);
    }
  }
}
