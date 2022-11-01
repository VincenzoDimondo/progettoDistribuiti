import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrazioneCompletataComponent } from './registrazione-completata.component';

describe('RegistrazioneCompletataComponent', () => {
  let component: RegistrazioneCompletataComponent;
  let fixture: ComponentFixture<RegistrazioneCompletataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrazioneCompletataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrazioneCompletataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
