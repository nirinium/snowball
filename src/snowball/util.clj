(ns snowball.util
  (:require [taoensso.timbre :as log]))

(defn poll-while [poll-ms pred-fn body-fn]
  (loop []
    (when (try
            (pred-fn)
            (catch Exception e
              (log/error "Caught an error in poll-while pred" (Throwable->map e))
              (.printStackTrace e)
              true))
      (try
        (body-fn)
        (catch Exception e
          (log/error "Caught an error in poll-while body" (Throwable->map e))
          (.printStackTrace e)))
      (Thread/sleep poll-ms)
      (recur))))
