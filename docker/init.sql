CREATE TABLE IF NOT EXISTS playground (
    k text PRIMARY KEY,
    v text NOT NULL
);

INSERT INTO playground(k, v) VALUES ('foo', 'bar') ON CONFLICT (k) DO UPDATE SET k=EXCLUDED.k;
