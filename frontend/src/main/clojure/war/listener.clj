(ns war.listener
  (:require [sencha.database :as database])
  (:gen-class :implements [javax.servlet.ServletContextListener]))
  
(defn -contextInitialized [this e]
  (database/-main))
  
(defn -contextDestroyed [this e] )
