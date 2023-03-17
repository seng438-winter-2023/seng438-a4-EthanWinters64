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

class TestAddtoCart():
  def setup_method(self, method):
    self.driver = webdriver.Chrome()
    self.vars = {}
  
  def teardown_method(self, method):
    self.driver.quit()
  
  def test_addtoCart(self):
    self.driver.get("https://www.ikea.com/ca/en/")
    self.driver.set_window_size(1920, 1080)
    self.driver.find_element(By.LINK_TEXT, "Products").click()
    self.driver.find_element(By.LINK_TEXT, "New products").click()
    WebDriverWait(self.driver, 30).until(expected_conditions.presence_of_element_located((By.CSS_SELECTOR, ".pub__carousel-slide:nth-child(2) .pip-btn--icon-emphasised")))
    self.driver.execute_script("window.scrollTo(0,712)")
    self.driver.find_element(By.CSS_SELECTOR, ".pub__carousel").click()
    self.driver.find_element(By.CSS_SELECTOR, ".pub__carousel-slide:nth-child(2) .pip-btn--icon-emphasised").click()
    self.driver.execute_script("window.scrollTo(0,0)")
    self.driver.find_element(By.CSS_SELECTOR, ".js-shopping-cart-icon").click()
    self.driver.execute_script("window.scrollTo(0,300)")
    WebDriverWait(self.driver, 30).until(expected_conditions.presence_of_element_located((By.CSS_SELECTOR, ".cart-ingka-btn__inner")))
    elements = self.driver.find_elements(By.LINK_TEXT, "Remove product")
    assert len(elements) > 0
  