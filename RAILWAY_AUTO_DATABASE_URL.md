# Railway Auto-Set DATABASE_URL - The Right Way

## The Problem

You keep manually setting `DATABASE_URL`, but Railway keeps resetting it because **Railway automatically sets it** when services are linked.

## ✅ The Solution: Let Railway Auto-Set It

**Don't manually set `DATABASE_URL`!** Railway does this automatically.

### Step 1: Link the Services (If Not Already Linked)

1. Go to `flow-platform` service
2. Click **Settings** tab
3. Scroll to **Service Dependencies** (or **Connected Services**)
4. Click **Add Service** or **Connect Database**
5. Select `flow-db` (your PostgreSQL service)
6. Railway will **automatically** add `DATABASE_URL` variable

### Step 2: Verify DATABASE_URL is Auto-Set

1. Go to `flow-platform` → **Variables** tab
2. Look for `DATABASE_URL`
3. It should be there automatically (Railway sets it)
4. **Don't edit it!** Railway manages this

### Step 3: Set Only These Manual Variables

In `flow-platform` → Variables, you only need to manually set:

- ✅ `DATABASE_USER` = `postgres` (optional - defaults to postgres)
- ✅ `DATABASE_PASSWORD` = (from flow-db → Variables → `PGPASSWORD`)
- ✅ `JWT_SECRET` = `d8635b0aab843c152ee6711673593f5b67c563b769c02fd04a5730577e503aa3`
- ✅ `MASTER_ENCRYPTION_KEY` = `e103e74a7c9f87e7b4b608a8c55114220f91960a7083df431152725cb0d1e734`

**Do NOT manually set `DATABASE_URL`** - Railway handles it!

## If DATABASE_URL is Missing

If `DATABASE_URL` doesn't appear after linking:

1. Make sure `flow-db` is linked to `flow-platform`
2. Check `flow-platform` → Settings → Service Dependencies
3. If not linked, add `flow-db` as a dependency
4. Railway will auto-set `DATABASE_URL` within seconds

## Why This Happens

- Railway **automatically** sets `DATABASE_URL` when PostgreSQL is linked
- If you manually set it, Railway might overwrite it on redeploy
- The solution: **Let Railway manage `DATABASE_URL`** and only set the other variables manually

## Quick Checklist

- [ ] `flow-db` is linked to `flow-platform` (Settings → Service Dependencies)
- [ ] `DATABASE_URL` appears automatically in Variables (don't edit it!)
- [ ] `DATABASE_USER` = `postgres` (manually set)
- [ ] `DATABASE_PASSWORD` = (manually set from flow-db)
- [ ] `JWT_SECRET` = (manually set)
- [ ] `MASTER_ENCRYPTION_KEY` = (manually set)

## Summary

**Stop manually setting `DATABASE_URL`!** Link the services and let Railway handle it automatically.

