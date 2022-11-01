import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrazioneFallitaComponent } from './registrazione-fallita.component';

describe('RegistrazioneFallitaComponent', () => {
  let component: RegistrazioneFallitaComponent;
  let fixture: ComponentFixture<RegistrazioneFallitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrazioneFallitaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrazioneFallitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
