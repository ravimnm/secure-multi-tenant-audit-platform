import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityList } from './security-list';

describe('SecurityList', () => {
  let component: SecurityList;
  let fixture: ComponentFixture<SecurityList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SecurityList],
    }).compileComponents();

    fixture = TestBed.createComponent(SecurityList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
