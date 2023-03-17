# Generated by Selenium IDE
import pytest
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities

class TestJoinIKEAFamily():
  def setup_method(self, method):
    self.driver = webdriver.Chrome()
    self.vars = {}
  
  def teardown_method(self, method):
    self.driver.quit()
  
  def test_joinIKEAFamily(self):
    self.driver.get("https://www.ikea.com/ca/en/")
    self.driver.find_element(By.CSS_SELECTOR, ".hnf-message__content__item:nth-child(2) .hnf-message__copy").click()
    self.driver.find_element(By.ID, "family-signup-form-firstName").click()
    self.driver.find_element(By.ID, "family-signup-form-firstName").send_keys("Ethan")
    self.driver.find_element(By.ID, "family-signup-form-lastName").click()
    self.driver.find_element(By.ID, "family-signup-form-lastName").send_keys("Winters")
    self.driver.find_element(By.ID, "family-signup-form-birthDate").click()
    self.driver.find_element(By.ID, "family-signup-form-birthDate").send_keys("02-12-2001")
    self.driver.find_element(By.ID, "family-signup-form-address").click()
    self.driver.execute_script("window.scrollTo(0,10)")
    self.driver.find_element(By.ID, "family-signup-form-address").send_keys("11207 26th st SW")
    self.driver.find_element(By.ID, "family-signup-form-preferredStore").click()
    dropdown = self.driver.find_element(By.ID, "family-signup-form-preferredStore")
    dropdown.find_element(By.XPATH, "//option[. = 'Calgary']").click()
    self.driver.find_element(By.ID, "family-signup-form-phoneNumber").click()
    self.driver.find_element(By.ID, "family-signup-form-phoneNumber").send_keys("4033706238")
    self.driver.find_element(By.ID, "family-signup-form-email").click()
    self.driver.find_element(By.ID, "family-signup-form-email").send_keys("emjw01@gmail.com")
    self.driver.find_element(By.ID, "family-signup-form-password").click()
    self.driver.find_element(By.ID, "family-signup-form-password").send_keys("emjw01@gmail.com")
    self.driver.find_element(By.ID, "family-signup-form-contact-method-communications").click()
    self.driver.find_element(By.ID, "family-signup-form-contact-method-allowSMS").click()
    self.driver.find_element(By.CSS_SELECTOR, ".form-field:nth-child(23) .checkbox__symbol").click()
    self.driver.find_element(By.ID, "family-signup-form-double-consent").click()
  