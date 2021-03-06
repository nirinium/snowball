(ns snowball.main
  (:require [clojure.edn :as edn]
            [clojure.tools.nrepl.server :as nrepl]
            [cider.nrepl]
            [bounce.system :as b]
            [taoensso.timbre :as log]))

(defn -main []
  (let [port (-> (slurp ".nrepl-port") (edn/read-string))]
    (log/info "Starting nREPL server on port" port)
    (nrepl/start-server :bind "0.0.0.0"
                        :port port
                        :handler (ns-resolve 'cider.nrepl 'cider-nrepl-handler)))

  (log/info "Starting components...")
  (b/set-opts! #{'snowball.config/value
                 'snowball.discord/audio-chan
                 'snowball.comprehension/phrase-text-chan
                 'snowball.speech/synthesiser
                 'snowball.presence/poller
                 'snowball.command/dispatcher})
  (b/start!)
  (log/info "Everything's up and running!"))
