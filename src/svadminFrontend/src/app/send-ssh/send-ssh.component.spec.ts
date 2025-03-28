import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendSSHComponent } from './send-ssh.component';

describe('SendSSHComponent', () => {
  let component: SendSSHComponent;
  let fixture: ComponentFixture<SendSSHComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SendSSHComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendSSHComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
