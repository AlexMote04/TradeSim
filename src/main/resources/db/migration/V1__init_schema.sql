-- Users Table
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Accounts Table (Fiat Balance)
CREATE TABLE accounts (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL REFERENCES users(id),
                          balance NUMERIC(19, 4) NOT NULL DEFAULT 0.0000,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Assets Table (Holdings)
CREATE TABLE assets (
                        id UUID PRIMARY KEY,
                        account_id UUID NOT NULL REFERENCES accounts(id),
                        ticker VARCHAR(10) NOT NULL,
                        quantity INTEGER NOT NULL DEFAULT 0,
                        UNIQUE(account_id, ticker) -- A user's account should only have one row per ticker
);

-- Orders Table (Intent to trade)
CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        account_id UUID NOT NULL REFERENCES accounts(id),
                        ticker VARCHAR(10) NOT NULL,
                        side VARCHAR(4) NOT NULL CHECK (side IN ('BUY', 'SELL')),
                        status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
                        price NUMERIC(19, 4) NOT NULL,
                        quantity INTEGER NOT NULL,
                        created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Trades Table (The Immutable Ledger)
CREATE TABLE trades (
                        id UUID PRIMARY KEY,
                        buy_order_id UUID NOT NULL REFERENCES orders(id),
                        sell_order_id UUID NOT NULL REFERENCES orders(id),
                        execution_price NUMERIC(19, 4) NOT NULL,
                        quantity INTEGER NOT NULL,
                        executed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);