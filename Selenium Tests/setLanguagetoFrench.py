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

class TestSetLanguagetoFrench():
  def setup_method(self, method):
    self.driver = webdriver.Chrome()
    self.vars = {}
  
  def teardown_method(self, method):
    self.driver.quit()
  
  def test_setLanguagetoFrench(self):
    self.driver.get("https://www.ikea.com/ca/en/")
    self.driver.find_element(By.CSS_SELECTOR, ".hnf-btn--open-menu > .hnf-btn__inner").click()
    self.driver.find_element(By.CSS_SELECTOR, "select").click()
    dropdown = self.driver.find_element(By.CSS_SELECTOR, "select")
    dropdown.find_element(By.XPATH, "//option[. = 'Français']").click()
    elements = self.driver.find_elements(By.LINK_TEXT, "Meilleurs prix")
    assert len(elements) > 0
  
