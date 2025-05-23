import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSavingAccountComponent } from './create-saving-account.component';

describe('CreateSavingAccountComponent', () => {
  let component: CreateSavingAccountComponent;
  let fixture: ComponentFixture<CreateSavingAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateSavingAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSavingAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
