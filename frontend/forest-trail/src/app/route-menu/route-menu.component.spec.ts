import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RouteMenuComponent } from './route-menu.component';

describe('RouteMenuComponent', () => {
  let component: RouteMenuComponent;
  let fixture: ComponentFixture<RouteMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RouteMenuComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RouteMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
