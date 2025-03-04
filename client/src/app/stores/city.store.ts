import { Injectable } from '@angular/core';
import { ComponentStore } from '@ngrx/component-store';
import { tap, switchMap } from 'rxjs/operators';
import { Observable, from } from 'rxjs';
import { db } from '../shared/app.db';
import { City } from '../model/city';
import { liveQuery } from 'dexie';

export interface CityState {
  cities: City[];
  loading: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class CityStore extends ComponentStore<CityState> {
  constructor() {
    super({
      cities: [],
      loading: false,
    });
  }

  // === Selectors ===
  readonly cities$ = this.select((state) => state.cities);
  readonly loading$ = this.select((state) => state.loading);

  // === Updaters ===
  private readonly setLoading = this.updater<boolean>((state, loading) => ({
    ...state,
    loading,
  }));

  private readonly setCities = this.updater<City[]>((state, cities) => ({
    ...state,
    cities,
  }));

  // === Effects ===
  readonly loadCities = this.effect((trigger$: Observable<void>) =>
    trigger$.pipe(
      tap(() => this.setLoading(true)),
      switchMap(() =>
        from(liveQuery(() => db.cities
              .reverse().toArray())).pipe(
          tap({
            next: (cities) => {
              this.setCities(cities);
              this.setLoading(false);
            },
            error: () => this.setLoading(false),
          })
        )
      )
    )
  );

  readonly addNewCity = this.effect((cityInput$: Observable<{ code: string, cityName: string }>) =>
    cityInput$.pipe(
      switchMap(({ code, cityName }) =>
        from(
          db.addCity({
            code: code,
            city_name: cityName
          })
        ).pipe(
          // After adding, reload or manually add to local store
          tap(() => this.loadCities())
        )
      )
    )
  );


}