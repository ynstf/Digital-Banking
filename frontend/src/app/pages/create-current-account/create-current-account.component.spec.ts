import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCurrentAccountComponent } from './create-current-account.component';

describe('CreateCurrentAccountComponent', () => {
  let component: CreateCurrentAccountComponent;
  let fixture: ComponentFixture<CreateCurrentAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateCurrentAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateCurrentAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
